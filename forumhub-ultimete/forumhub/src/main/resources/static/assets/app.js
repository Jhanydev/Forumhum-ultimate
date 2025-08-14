const storage = JSON.parse(localStorage.getItem('forumhub')||'{}');
function saveStorage(){ localStorage.setItem('forumhub', JSON.stringify(storage)); }
async function api(path, opts={}){
  const headers = {'Content-Type':'application/json'};
  if(storage.token) headers['Authorization'] = 'Bearer ' + storage.token;
  const o = {method:'GET', ...opts, headers:{...headers, ...(opts.headers||{})}};
  if (opts.body && !(opts.body instanceof FormData)) o.body = JSON.stringify(opts.body);
  const res = await fetch('/api'+path, o);
  if(!res.ok) throw new Error(await res.text());
  const ct = res.headers.get('Content-Type')||'';
  return ct.includes('application/json') ? res.json() : res.text();
}

async function loadTopics(){
  const page = await api('/topics');
  const container = document.getElementById('topics');
  if(!container) return;
  container.innerHTML = '';
  page.content.forEach(t => {
    const tags = (t.tags||[]).map(x=>`<span class="badge">#${x}</span>`).join(' ');
    const el = document.createElement('div');
    el.className = 'card topic';
    el.innerHTML = `
      <h3>${t.title}</h3>
      <p>${t.message}</p>
      <div>${tags}</div>
      <div class="muted">Curso: <span class="badge">${t.course}</span> · Autor: ${t.authorName} · Status: ${t.status} · Likes: ${t.likeCount} · Criado em: ${new Date(t.createdAt).toLocaleString()}</div>
      <div style="margin-top:8px;">
        ${storage.token ? `<button class="btn" onclick="likeTopic(${t.id})">Curtir</button>` : ''}
      </div>
    `;
    container.appendChild(el);
  });
}
async function likeTopic(id){
  try{
    const count = await api(`/topics/${id}/like`, {method:'POST'});
    alert('Curtidas: ' + count);
    loadTopics();
  }catch(e){ alert('Erro: ' + e.message); }
}
loadTopics();
