import { h, Component } from 'preact';

class ResetPassword extends Component {

    constructor(props) {
        super(props);
        console.log(props);
        this.state = {email: null, password: null, message: null};
    }
    
    emailChange(event) {
        this.setState({email: event.target.value});
    }

    passwordChange(event) {
        this.setState({password: event.target.value});
    }

    onSubmit(email, password) {
        fetch(`/api/user/${email}/reset-password/${this.props.matches.key}`, {
            method: 'post',
            body: password,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
        }).then(res => {
            if (res.status >= 200 && res.status < 300) {
                this.setState({message: 'Your password has been reset'});
            } else {
                let err = new Error(res.statusText);
                err.response = res;
                throw err;
            }
        });
    }

    render() {
        return (
            <main>
                { !this.state.message && (
                    <form style="display: flex; flex-direction: column;">
                        <input type="text" required placeholder="Email" name="username" value={this.state.email} onChange={(e) => this.emailChange(e)} />
                        <input type="password" required placeholder="Password" name="password" value={this.state.password} onChange={(e) => this.passwordChange(e)} />
                        <button type="button" style="width:60px" onClick={() => this.onSubmit(this.state.email, this.state.password)}>OK</button>
                    </form>
                )}
                { this.state.message }
            </main>
        );
    }
}

export default ResetPassword;
