Easy-ABAC Framework
===========================

Latest news
-----------
- 23/01/2020: Version 1.0-RC1 is out!

What is Easy-ABAC Framework?
----------------------------

_`The description is about to be updated.`_

The aim of the **Easy-ABAC Framework** is to help you protect your REST resources from unauthorized access.
The framework provides flexible access rights configuration.

Fairly often REST resources remain unprotected. The framework will help to find and fix that. 
Application will raise compilation error if any resource remains unprotected.

Core features
-------------
- Lightweight library and easy to learn API
- Declarative authorization
- **Compile-time** check of missing authorization of REST resources
- Built for Spring based applications


How it works
------------

**How to build?**
```
mvn clean install 
```

**How to use?**

To start working with the **Easy-ABAC Framework** you need to add the easy-abac-{version}.jar to the classpath or add it as
a maven dependency, like this:
```xml
<dependency>
    <groupId>com.exadel</groupId>
    <artifactId>easy-abac</artifactId>
    <version>${abac-version}</version>
</dependency>
```

**Core Attributes**
- ```Action``` interface - to define possible actions with entity
- ```@Access``` annotation - to define custom annotation to restrict access to entity
- ```@Id``` annotation - to define entity identifier parameter in method
- ```EntityAccessValidator``` interface - to define access validation rules for entity(s)
- ```@ProtectedResource``` and ```@PublicResource``` annotations - to turn on / turn of easy-abac validation respectively

**Example**

Let's consider simple example: you have resource (entity) ```Project```, CRUD operations with them and would like to restrict access to the resource.

**1.** Define available actions for your resource. For example:
```java
    public enum ProjectAction implements com.exadel.easyabac.model.Action {
        VIEW,
        UPDATE,
        CREATE,
        DELETE
    }
```
Actions are used to differentiate access rights to the resource, 
each authenticated user may have different set of available actions. 
Further will be described have to attach these actions to user.

**2.**: Create your entity's entityId annotation :
```java
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface ProjectId {
    }
```

The ```@ProjectId``` annotation will help us define entity identifier parameter in controller or service method:
```java
public ResponseEntity get(@ProjectId @PathVariable("projectId") Long projectId) {...}
```

**3.**: Define the annotation which protects your REST resource.
```java
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Access(id = ProjectId.class)
    public @interface ProjectAccess {
  
        /* required actions, see Step 1 */
        ProjectAction[] value();
        
        /* Choose your validator */
        Class<? extends EntityAccessValidator> validator() default ProjectValidator.class;
    }
```
The ```@ProjectAccess``` annotation will be used to protect REST methods. Here you should define type of actions created in _Step 1_ as well as validator.

**4.**: Implement the EntityAccessValidator interface for this resource, where the authorization logic is implemented:
```java
    public class ProjectValidator implements com.exadel.easyabac.aspect.EntityAccessValidator<ProjectAction> {
        @Override
        public void validate(ExecutionContext<ProjectAction> context) {
            //your validation logic here
        }
    }
```
The validator might be defined as simple class as well as Spring component.
If your validator throws any exception, the access to the resource is denied.
The ExecutionContext have the following attributes you can use for validation and logging:
- Entity identifier (Project identifier in our case)
- Set of actions which are required to access the resource.
- The action class type.
- The ```org.aspectj.lang.JoinPoint``` which contains more details about protected method.

**5.**: Protect your REST endpoints using your annotations.

```java
    @RequestMapping("/{projectId}")
    @ProtectedResource
    @ProjectAccess(ProjectAction.VIEW)
    public class ProjectController {
        
        @RequestMapping("/get")
        public Project get(@ProjectId @PathVariable("projectId") Long projectId) {
            // your code here
        }    
        
        @ProjectAccess(ProjectAction.UPDATE)
        @RequestMapping("/update")
        public Project update(@ProjectId @PathVariable("projectId") Long projectId) {
            //your code here
        }    

        @ProjectAccess(ProjectAction.DELETE)
        @RequestMapping("/delete")
        public Project delete(@PathVariable("projectId") Long projectId) {
             //your code here
        }
        
        @PublicResource // turns off EASY-ABAC validation
        public Project close() {
            //your code here
        }
    }
```

```@ProtectedResource``` is a class-level annotation to turn on easy-abac validation. 
All public instance methods will be protected unless ```@ProtectedResource``` is provided on method, to turn off easy-abac validation.

```@ProjectAccess(ProjectAction.VIEW)``` construction says that only users which have 
```ProjectAction.VIEW``` action will have access right to the Project.

```@ProjectAccess``` can restricted access as globally when used on class level as locally for particular method.
When used both on class and method levels then set of actions are added up together. 
For example, ```ProjectController.update``` requires two actions - ```ProjectAction.VIEW``` & ```ProjectAction.UPDATE```.

**Compile time checks**

The easy-abac provides user-friendly compile time checks: 

- This will raise compile-time error as ```@ProjectId``` annotation is missing:
```java
        @ProjectAccess(ProjectAction.DELETE)
        @RequestMapping("/delete")
        public Project delete(@PathVariable("projectId") Long projectId) {
             //your code here
        }
```

- This will raise compile-time error as ```@ProjectAccess``` annotation is missing while ```@ProjectId``` provided:
```java
        @RequestMapping("/delete")
        public Project delete(@ProjectId @PathVariable("projectId") Long projectId) {
             //your code here
        }
```

- This will raise compile-time error as ```@ProjectAccess``` annotation is missing while resource is marked with ```@ProtectedResource``` globally:
```java
    @ProtectedResource
    public class ProjectController {
  
        @RequestMapping("/delete")
        public Project delete(@ProjectId @PathVariable("projectId") Long projectId) {
             //your code here
        }
    }
```

- This will raise compile-time error as ```value()``` is missing while required:
```java
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Access(id = ProjectId.class)
    public @interface ProjectAccess {
  
        /* required actions, see Step 1 */
        ProjectAction[] value();
        
        /* Choose your validator */
        Class<? extends EntityAccessValidator> validator() default ProjectValidator.class;
    }
```

- This will raise compile-time error as ```validator()``` is missing while required:
```java
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Access(id = ProjectId.class)
    public @interface ProjectAccess {
  
        /* required actions, see Step 1 */
        ProjectAction[] value();
    }
```

**Custom validator implementation**

Let's consider example for validator implementation.

```java
@Component
public class GeneralEntityAccessValidator implements EntityAccessValidator<Action> {

    private static final String ERROR_TEMPLATE = "Access to entity[id=%s] denied.";

    @Autowired
    private ActionProvider actionProvider;

    @Autowired
    private DemoAuthorization authorization;

    @Override
    public void validate(ExecutionContext<Action> context) {
        Long entityId = context.getEntityId();
        Set<Action> availableActions = actionProvider.getAvailableActions(entityId, context.getActionType());
        Set<Action> requiredActions = context.getRequiredActions();

        Set<Action> missingActions = SetUtils.difference(requiredActions, availableActions);
        if (CollectionUtils.isEmpty(missingActions)) {
            return;
        }

        AccessResponse response = new AccessResponse(
                authorization.getLoggedUserRole(),
                entityId,
                missingActions,
                context.getJoinPoint().getSignature().toString()
        );
        throw new AccessException(String.format(ERROR_TEMPLATE, entityId), response);
    }
}
```
```ActionProvider``` is provider of actions is available for current logged in user.
Here we calculate difference between actions available for user and required actions.
In case when user missing some required actions - ```AccessException``` is thrown.
Further you're free to handle this exception. For example using ```ExceptionHandler```.

```java
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ACCESS_DENIED_PAGE = "403.html";

    @ExceptionHandler(AccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(AccessException exception, Model model) {
        model.addAttribute(exception.getResponse());
        return ACCESS_DENIED_PAGE;
    }
}
```

**Why actions instead of permissions?**

We consider the following concepts:
- _Permissions_ - static user access rights, which cannot be changed depending on any other facts.
- _Actions_ - dynamic user access rights, which can be changed depending on any other facts.

**Example:** 

User has ```ProjectAction.UPDATE```, but we need to restrict it depending on some project attributes.
For example user should be unable to update projects with status 'closed'. 
So project action ```ProjectAction.UPDATE``` is available only for not 'closed' projects. 
This can be simply implemented as an action provider, 
which takes user static actions and then filtering them using some dynamic attributes.
This also works for static actions. 

-------------

You can see the framework in action in [easy-abac-demo](easy-abac-demo/README.md)

Project structure:
- [easy-abac](easy-abac/README.md)
	- [abac-annotation-processing](easy-abac/abac-annotation-processing/README.md)
	- [abac-aspect](easy-abac/abac-aspect/README.md)
	- [abac-model](easy-abac/abac-model/README.md)
- [easy-abac-demo](easy-abac-demo/README.md) – example