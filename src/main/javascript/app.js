import { route, Router } from 'preact-router';
import { h, render, Component } from 'preact';

import Home from 'views/home/home';
import Community from 'views/community/community';
import Admin from 'views/admin/admin';
import SignIn from 'views/auth/sign-in';
import ForgottenPassword from 'views/auth/forgotten-password';
import ResetPassword from 'views/auth/reset-password';

import Header from 'components/header/header';
import SideMenu from 'components/side-menu/side-menu';

import css from './app.css';

const initState = { token: null, communities: [], email: null, name: null };

class App extends Component {
    
    constructor(props) {
        super(props);
        this.state = {};
        const user = sessionStorage.getItem('user');
        if (user) {
            this.setState(JSON.parse(user));
        }
    }

    componentDidMount() {
        if (window.location.search) {
            const keyParamIdx = window.location.search.indexOf('key=');
            if (keyParamIdx > 0) {
                const keyValue = window.location.search.substring(keyParamIdx + 4);
                route(`/reset-password/${keyValue}`);
            }
        }
    }

    signin(email, password) {
        fetch(`/api/user/${email}/sign-in`, {
            method: 'post',
            body: password,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
        }).then(res => {
            if (res.status >= 200 && res.status < 300) {
                return res.json();
            } else {
                let err = new Error(res.statusText);
                err.response = res;
                throw err;
            }
        })
        .then(data => { 
            this.setState(data);
            sessionStorage.setItem('user', JSON.stringify(data));
            sessionStorage.setItem('token', `Bearer${data.token}`);
            route('/');
        });
    }

    signout() {
        sessionStorage.setItem('user', null);
        sessionStorage.setItem('token', null);
        this.setState(Object.assign({}, initState));
    }

    render() {
        return (
        <article className="app">
            <Header title="Your free ads here 😎" />
            <div style="display: flex">
                <SideMenu signout={ () =>  this.signout() } username={ this.state.name } communities={this.state.communities}></SideMenu>
                <Router>
                    <Home path="/" />
                    <Community path="/community" />
                    <SignIn path="/sign-in" onSubmit={(email, password) => this.signin(email, password)} />
                    <ForgottenPassword path="/forgotten-password" />
                    <ResetPassword path="/reset-password/:key" />
                    <Admin path="/admin" />
                </Router>
            </div>
        </article>);
    }
}

render(<App />, document.body);

if (module.hot) {
    module.hot.accept('views/home/home', renderApp);
}

if (process.env.NODE_ENV === 'production') {
    const runtime = require('offline-plugin/runtime');

    runtime.install({
        onUpdateReady: () => {
            // Tells to new SW to take control immediately
            runtime.applyUpdate();
        },
        onUpdated: () => {
            // Reload the webpage to load into the new version
            window.location.reload();
        },
    });
}
