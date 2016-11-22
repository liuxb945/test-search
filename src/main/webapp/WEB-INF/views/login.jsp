<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>  
<html>  
  <head>  
    <title>webmvct简单例子</title>  
    <meta charset="utf-8">      
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <!-- Bootstrap -->  
    <link rel="stylesheet" href="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/css/bootstrap.min.css">  
    <link rel="stylesheet" href="http://v3.bootcss.com/examples/signin/signin.css"/>  
   <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->  
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->  
    <!--[if lt IE 9]>  
        <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>  
        <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>  
    <![endif]-->  
     <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->  
    <script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>  
    <!-- Include all compiled plugins (below), or include individual files as needed -->  
    <script src="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/js/bootstrap.min.js"></script>   
  </head>  
  <body>  
  <div  class="container">  
      <form class="form-signin" role="form" action="login">  
        <h2 class="form-signin-heading">登陆</h2>  
        <input type="text" name="username" class="form-control" placeholder="请输入用户名" required autofocus>  
    
        <button class="btn btn-lg btn-primary btn-block" type="submit">登陆</button>  
      </form>  
     
  </body>  
</html>