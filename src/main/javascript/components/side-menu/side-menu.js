import { h } from 'preact';

const SideMenu = (props) => (
    <section className="side-menu">
        { props.username && (<nav style="display: flex" className="menu-item">👤 { props.username }</nav> )}
        <nav className="menu-item"><a href="/">Home</a></nav>
        { props.username && (<nav className="menu-item"><a href="/community">Community</a></nav>) }
        { props.communities && !!props.communities.find(c => c.name.indexOf('admin') >= 0) && (<nav className="menu-item"><a href="/admin">Admin</a></nav>) }
        { props.username && (<nav className="menu-item" onclick={ () => props.signout() }><a href="#">Sign-out</a></nav> )}
        { !props.username && (<nav className="menu-item"><a href="/sign-in">Sign-in</a></nav>) }
    </section>
);

export default SideMenu;