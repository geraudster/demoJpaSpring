<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="java.util.Collection"%>
<%@page import="com.valtech.testjpa.domain.User"%>
<%@page import="com.valtech.testjpa.service.UserService"%>
<html>
<body>
<h2>Hello World!</h2>

<% 
ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
UserService service = context.getBean(UserService.class);
out.println("Création de 2 users");
out.println("<br/>");
User user1 = new User();
user1.setFirstName("Toto");
user1.setLastName("DUPONT");
User created1 = service.updateUser(user1);

User user2 = new User();
user2.setFirstName("Titi");
user2.setLastName("DURAND");
User created2 = service.updateUser(user2);

out.println("Users créés: ");
out.println("<br/>");

Collection<User> userList = service.getAllUsers();
for(User u : userList) {
	out.println(u.getId() + ";" + u.getLastName() + ";" + u.getFirstName());
	out.println("<br/>");
}
%>
</body>
</html>
