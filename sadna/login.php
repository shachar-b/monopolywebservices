<?php include_once("header.php"); ?>

<div class='form-container'>
<form class='well span4' id='login-form' action='' method='POST'>
	<h1>Login</h1>
	<br />
	<label for='email'>Email</label>
	<input type='email' name='email' id='email' />
	<label for='password'>Password</label>
	<input type='password' name='password' id='password' />
	<br />
	<button class='btn' type='submit'>Login</button>
	<br />
	<br />
	<p>Don't have a user?<a href='signup.php'>sign up now!</a></p>
</form>
</div>
<?php include_once("footer.php"); ?>