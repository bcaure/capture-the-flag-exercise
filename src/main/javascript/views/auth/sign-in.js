import { h, Component } from 'preact';

class SignIn extends Component {

    constructor(props) {
        super(props);
        this.state = {email: null, password: null};
    }
    
    emailChange(event) {
        this.setState({email: event.target.value});
    }

    passwordChange(event) {
        this.setState({password: event.target.value});
    }

    render() {
        return (
            <main>
                <form style="display: flex; flex-direction: column;">
                    <input type="text" required placeholder="Email" name="username" value={this.state.email} onChange={(e) => this.emailChange(e)} />
                    <input type="password" required placeholder="Password" name="password" value={this.state.password} onChange={(e) => this.passwordChange(e)} />
                    <a href="/forgotten-password">Forget your password?</a>
                    <button type="button" style="width:60px" onClick={() => this.props.onSubmit(this.state.email, this.state.password)}>OK</button>
                </form>
            </main>
        );
    }
}

export default SignIn;
