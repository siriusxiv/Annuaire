package connections;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import play.Play;

/**
 * Requêtes LDAP pour se connecter
 * Attention ! On ne peut pas se connecter à LDAP si on utilise le réseau Wifi de Centrale, il faut
 * être branché en Ethernet !
 * @author Admin
 *
 */
public class LDAP{
	private static String serveur = Play.application().configuration().getString("ldap.url");
	
	/**
	 * Vérifie que le login et le mot de passe sont corrects et sont ceux d'un professeur.
	 * @param login
	 * @param passw
	 * @return VRAI si le login et le mot de passe sont corrects, FAUX sinon.
	 */
	public static boolean check(String login, String passw){
		if(Play.application().configuration().getString("test.mode").equals("on") && login.startsWith("test")){
			return true;
		}
		Hashtable<String,String> properties = new Hashtable<String,String>();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		properties.put(Context.PROVIDER_URL, serveur);
		properties.put(Context.SECURITY_AUTHENTICATION, "simple");
		properties.put(Context.SECURITY_PRINCIPAL, "uid="+login+", ou=people, dc=ec-nantes, dc=fr");
		properties.put(Context.SECURITY_CREDENTIALS, passw);
        try {
        	System.out.println("trying to get identified...");
        	DirContext ctx = new InitialDirContext(properties);
        	System.out.println("identified");
            ctx.close();
		    return true;
        } catch (NamingException e) {
        	System.out.println(e.getMessage());
            return false;
        }
	}
}
