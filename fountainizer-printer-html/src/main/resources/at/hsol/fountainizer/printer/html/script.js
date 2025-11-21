 function createPage(extraClass) {
      const page = document.createElement('div');
      page.className = 'page' + (extraClass ? ' ' + extraClass : '');
      return page;
    }

    // Build logical blocks:
    // - Titlepage blocks.
    // - Dialogue blocks: CHARACTER + following PARENTHETICAL/DIALOGUE.
    // - Single-element blocks for others (heading, action, transition, etc.)
    function buildBlocks(source) {
      const blocks = [];
      const children = Array.from(source.children);
      let i = 0;

      while (i < children.length) {
        const el = children[i];

        // Titlepage container as its own block
        if (el.classList.contains('titlepage')) {
          blocks.push({ type: 'titlepage', nodes: [el] });
          i++;
          continue;
        }

        // Dialogue block: character + (parenthetical/dialogue)+
        if (el.classList.contains('character')) {
          const group = [el];
          let j = i + 1;
          while (j < children.length) {
            const next = children[j];
            if (!next.classList) break;
            if (next.classList.contains('dialogue') || next.classList.contains('parenthetical')) {
              group.push(next);
              j++;
            } else {
              break;
            }
          }
          blocks.push({ type: 'block', nodes: group, isDialogue: true });
          i = j;
          continue;
        }

        // Fallback: single element block
        blocks.push({ type: 'block', nodes: [el] });
        i++;
      }

      return blocks;
    }

    function addPageNumbers() {
      const pages = Array.from(document.querySelectorAll('.document .page'));
      let num = 1;
      pages.forEach(page => {
        // Skip titlepage from numbering
        if (page.classList.contains('titlepage')) {
          const existing = page.querySelector('.page-number');
          if (existing) existing.remove();
          return;
        }

        const existing = page.querySelector('.page-number');
        if (existing) existing.remove();

        const label = document.createElement('div');
        label.className = 'page-number';
        label.textContent = num++;
        page.appendChild(label);
      });
    }

    function paginate() {
      const docEl = document.getElementById('document');
      const source = document.getElementById('script-content');
      const blocks = buildBlocks(source);

      docEl.innerHTML = '';

      let currentPage = null;

      function ensurePage(extraClass) {
        if (!currentPage) {
          currentPage = createPage(extraClass);
          docEl.appendChild(currentPage);
        }
      }

      for (const block of blocks) {
        if (block.type === 'titlepage') {
          // Titlepage: forced standalone page
          const page = createPage('titlepage');

          // Clone children of the titlepage container
          const src = block.nodes[0];
          Array.from(src.childNodes).forEach(node => {
            page.appendChild(node.cloneNode(true));
          });

          docEl.appendChild(page);
          currentPage = null; // reset for next normal page
          continue;
        }

        ensurePage();

        // Prepare fragment for this block
        const frag = document.createDocumentFragment();
        block.nodes.forEach(node => frag.appendChild(node.cloneNode(true)));

        // Tentatively append
        currentPage.appendChild(frag);

        // If it overflows, move whole block to a new page
        if (currentPage.scrollHeight > currentPage.clientHeight) {
          // Remove just-added nodes
          for (let k = 0; k < block.nodes.length; k++) {
            currentPage.removeChild(currentPage.lastChild);
          }

          // New page
          currentPage = createPage();
          docEl.appendChild(currentPage);

          // Re-append full block here
          block.nodes.forEach(node => {
            currentPage.appendChild(node.cloneNode(true));
          });
        }
      }

      source.style.display = 'none';
      addPageNumbers();
    }

    window.addEventListener('load', paginate);
    window.addEventListener('resize', () => {
      const docEl = document.getElementById('document');
      const source = document.getElementById('script-content');
      docEl.innerHTML = '';
      source.style.display = ''; // make sure source is visible for measurement/cloning
      paginate();
    });