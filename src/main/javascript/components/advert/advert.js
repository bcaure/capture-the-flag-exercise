import { h } from 'preact';

const Advert = (props) => (
    <article className="card">
        <header><strong>{ props.title }</strong></header>
        <p id={props.id}></p>
        <script>
            var html = `{ props.content }`;
            var container = document.getElementById('{ props.id }');
            var range = document.createRange();
            range.setStart(container, 0);
            container.appendChild(range.createContextualFragment(html));
        </script>
        <footer>Author: <a href={'mailto:' + props.email}>{ props.author }</a> pour { props.community }</footer>
    </article>
);

export default Advert;