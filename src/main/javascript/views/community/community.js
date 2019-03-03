import { h, Component } from 'preact';

class Community extends Component {

    constructor(props) {
        super(props);
        this.state = { communities: [] };
    }

    componentDidMount() {
        fetch('/api/user', { headers: { 'Authorization': sessionStorage.getItem('token') } })
        .then(response => response.json())
        .then(communities => {
            this.setState({communities});
        });
    }

    render() {
        const cards = this.state.communities.map(community => {
            const users = community.users.map(user => <li><a href={'mailto:'+user.email}>{ user.name }</a></li>);
            return (<article className="card"><h3>{ community.name }</h3><ul>{ users }</ul></article>);
        });
        return (
            <main style="width: 100%">
                <h2>Your communities</h2>
                <p className="users" style="flex: 1; display: flex;">{ cards }</p>
            </main>
        );
    }
}

export default Community;
