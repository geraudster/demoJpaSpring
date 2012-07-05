demoJpaSpring
=============
En fait dans le web.xml, il faut ajouter le Listener suivant :
	  <context-param>
	    <param-name>contextConfigLocation</param-name>
	    <param-value>/WEB-INF/classes/applicationContext-jpa.xml</param-value>
	  </context-param>
	  <listener>
	    <listener-class>
	            org.springframework.web.context.ContextLoaderListener
	        </listener-class>
	  </listener>

Cela va entre autres charger le EntityManagerFactory (et donc lancer l'initialisation de la base). Cela devrait donc également démarrer quartz (s'il est bien configuré dans le applicationContext, je ne l'ai pas mis dans la démo).

Ensuite, au niveau de ton contrôleur ou de ta servlet (en fait à partir du moment où tu peux récupérer le ServletContext), tu récupère une référence au contexte :

	ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

Puis tu récupère ton service, comme d'hab:
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
