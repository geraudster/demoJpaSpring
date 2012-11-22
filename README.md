# demoJpaSpring

Exemple de projet basé sur JPA + Spring + Slf4j.

## Utilisation logback + slf4j

Ajouter les dépendances slf4j-api et logback-classic.

Fichier de configuration logback dans : src/main/resources/logback.xml

Déclaration d'un logger:

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	...
	@Service
	public class UserService {
		Logger logger = LoggerFactory.getLogger(UserService.class);


Exemple d'utilisation dans la classe UserService :

	public User updateUser(User user) {
		logger.info("Appel de updateUser");
		
		// Exemple d'utilisation de placeholders avec slf4j
		logger.debug("Modification du client {}, {}", user.getLastName(), user.getFirstName());


## Liaisons Spring <-> JPA
En fait dans le web.xml, il faut ajouter le Listener suivant :
	  &lt;context-param&gt;
	    &lt;param-name&gt;contextConfigLocation&lt;/param-name&gt;
	    &lt;param-value&gt;/WEB-INF/classes/applicationContext-jpa.xml&lt;/param-value&gt;
	  &lt;/context-param&gt;
	  &lt;listener&gt;
	    &lt;listener-class&gt;
	            org.springframework.web.context.ContextLoaderListener
	        &lt;/listener-class&gt;
	  &lt;/listener&gt;

Cela va entre autres charger le EntityManagerFactory (et donc lancer l'initialisation de la base). Cela devrait donc également démarrer quartz (s'il est bien configuré dans le applicationContext, je ne l'ai pas mis dans la démo).

Ensuite, au niveau de ton contrôleur ou de ta servlet (en fait à partir du moment où tu peux récupérer le ServletContext), tu récupère une référence au contexte :

	ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

Puis tu récupères ton service, comme d'hab:
	UserService service = context.getBean(UserService.class);

Dans l'exemple je l'ai mis dans la JSP, mais c'est un peu crado...

Par contre, cela veut dire qu'il faut déclarer tes services dans le applicationContext et y injecter les dao. Par exemple dans mon UserService, j'ai un @Autowired (qui fait que Spring y injecte le premier bean de type UserDAO):

	@Service
	public class UserService {
	
	    @Autowired
	    UserDAO dao;
	
	...
	}

Donc pour résumer, lorsque ton serveur d'appli reçoit une requête HTTP, ça se fait dans l'ordre suivant :
 Servlet/JSP -> [ Service -> DAO -> Objet du domaine (ou entité) ]
Ce qui est entre crochets est géré par Spring. Dans la démo ça fait : index.jsp -> [ UserService -> UserDAO -> User ]
