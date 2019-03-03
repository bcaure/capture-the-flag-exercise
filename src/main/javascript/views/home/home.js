import { h, Component } from 'preact';

import Advert from '../../components/advert/advert';

class Home extends Component {

    constructor(props) {
        super(props);
        this.state = { ads: [] };
    }

    componentDidMount() {
        fetch('/api/advert', { headers: { 'Authorization': sessionStorage.getItem('token') } })
            .then(response => response.json())
            .then(data => {
                this.setState({ads: data});
            });
    }

    render() {
        const ads = this.state.ads.map((ad, idx) => (<Advert id={'ad'+idx} title={ad.title} content={ad.content} author={ad.author.name} email={ad.author.email} community={ad.sponsor.name}></Advert>));
        return (
            <main style="display: flex; width: 100%">
                <p className="ads" style="flex: 1; display: flex;">{ ads }</p>
                <aside><img src="https://domain.me/wp-content/uploads/2014/04/dont-hack-them.png" style="width:300px; margin-left: 20px;" /></aside>
            </main>
        );
    }
}

export default Home;
