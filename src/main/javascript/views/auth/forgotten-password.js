import { h, Component } from 'preact';

class ForgottenPassword extends Component {

    constructor(props) {
        super(props);
        this.state = {email: null, message: null};
    }
    
    emailChange(event) {
        this.setState({email: event.target.value});
    }

    onSubmit(email) {
        fetch(encodeURI(`/api/user/${email}/forgotten-password`))
        .then(() => this.setState({message: 'Reset password mail has been sent to ' + email}))
    }

    render() {
        return (
            <main>
                { !this.state.message && (
                <form style="display: flex; flex-direction: column;">
                    <input type="text" style="margin: 10px" required placeholder="Email" name="username" value={this.state.email} onChange={(e) => this.emailChange(e)} />
                    <button type="button" style="margin: 10px" onClick={() => this.onSubmit(this.state.email)}>Reset password</button>
                </form>
                )}
                { this.state.message }
            </main>
        );
    }
}

export default ForgottenPassword;
