# hexagonal-architecture
An example project using hexagonal architecture. This particular project makes clear distinctions between the core and adapters by providing them as separate explicit maven modules.

## Modules
Brief description of each module.

### app
This is the runnable application. 

### audit
This is a secondary adapter which acts as an audit trail or event log. 

### core
This is the core domain, where all the business logic resides. There are maven-enforcer rules on this project to restrict the use of any other project modules in this module, forcing the abstraction between the core domain and it's ports.

### data
This is a secondary adapter which acts as the data persistence layer. 

### rest
This is a primary adapter that enables users to interact with the core domain.
