import { h, Component } from 'preact';

class Admin extends Component {

    constructor(props) {
        super(props);
        this.state = { configs : [] };
    }

    componentDidMount() {
        fetch('/api/admin', { method: 'get', headers: { 'Authorization': sessionStorage.getItem('token') } }).then(response => response.json())
            .then(data => this.setState({configs: data}));
    }

    render() {

        const configs = this.state.configs.map(config => (
            <article className="card">
                <header><strong>{ config.key }</strong></header>
                <textarea style="width: 100%">{ config.decryptedValue ? config.decryptedValue : config.value }</textarea>
            </article>
        ));

        return (
            <main style="width: 100%">
                <h2>Admin</h2>
                { configs }
            </main>
        );
    }
}

export default Admin;
