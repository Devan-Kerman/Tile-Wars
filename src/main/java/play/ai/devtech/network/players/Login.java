package play.ai.devtech.network.players;

public class Login {
	public final String username;
	public final String password;
	private final int hash;
	
	@Override
	public String toString() {
		return "Username: " + username + "\nPassword: " + password;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Login && (((Login)obj).password.equals(password) && ((Login)obj).username.equals(username));
	}
	
	@Override
	public int hashCode() {
		return hash;
	}
	
	public Login(String user, String pass) {
		username = user;
		password = pass;
		hash = username.hashCode()^password.hashCode();
	}
}
