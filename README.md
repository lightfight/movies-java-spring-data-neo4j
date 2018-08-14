

spring-boot-starter-data-rest
=============================

### pom.xml中添加REST

如果不需要则取消

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>


```

### 控制台输出

可以看到已经实现了自动的映射

```bash
"{[/{repository}/{id}/{property}],methods=[PATCH || PUT || POST],consumes=[application/json || application/x-spring-data-compact+json || text/uri-list],produces=[application/hal+json || application/json]}"
"{[/{repository}/{id}/{property}/{propertyId}],methods=[DELETE],produces=[application/hal+json || application/json]}"
"{[/{repository}/search],methods=[HEAD],produces=[application/hal+json || application/json]}"
"{[/{repository}/search],methods=[GET],produces=[application/hal+json || application/json]}"
"{[/{repository}/search],methods=[OPTIONS],produces=[application/hal+json || application/json]}"
"{[/{repository}/search/{search}],methods=[GET],produces=[application/hal+json || application/json]}"
"{[/{repository}/search/{search}],methods=[GET],produces=[application/x-spring-data-compact+json]}"
"{[/{repository}/search/{search}],methods=[OPTIONS],produces=[application/hal+json || application/json]}"
"{[/{repository}/search/{search}],methods=[HEAD],produces=[application/hal+json || application/json]}"
"{[/profile/{repository}],methods=[GET],produces=[application/schema+json]}"
"{[/profile],methods=[OPTIONS]}"
"{[/profile],methods=[GET]}"
"{[/profile/{repository}],methods=[OPTIONS],produces=[application/alps+json]}"

```

### 请求示例

http://localhost:8002/persons/8

返回

```json
{
  "name" : "Emil Eifrem",
  "born" : 1978,
  "_links" : {
    "self" : {
      "href" : "http://localhost:8002/persons/8"
    },
    "person" : {
      "href" : "http://localhost:8002/persons/8"
    },
    "movies" : {
      "href" : "http://localhost:8002/persons/8/movies"
    }
  }
}
```


参考文档
=======

1. 中文：https://springcloud.cc/spring-data-rest-zhcn.html
2. 英文：https://spring.io/guides/gs/accessing-data-rest

### 16.2.3.隐藏存储库CRUD方法
如果您不想在CrudRepository上公开保存或删除方法，则可以使用@RestResource(exported = false)设置来覆盖要关闭的方法并将注释放在覆盖版本上。例如，为了防止HTTP用户调用CrudRepository的删除方法，请覆盖所有这些删除方法，并将注释添加到覆盖方法中。

```java

@RepositoryRestResource(path = "people", rel = "people")
interface PersonRepository extends CrudRepository<Person, Long> {

  @Override
  @RestResource(exported = false)
  void delete(Long id);

  @Override
  @RestResource(exported = false)
  void delete(Person entity);
}
```
