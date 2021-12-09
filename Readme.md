`遇到的问题`

1. 老是显示验证码错误

```
redis获取数据强转String
```

2.登录未经过用户名密码filter

```
#前端loginForm username写为了name
#UsernamePasswordAuthenticationFilter默认配置是username，password
http://localhost:8090/login?
username=admin&password=123456&code=abcde&token=11111
```