<?php include_once("header.php"); ?>
<?php $authenticateLogin->signup($_POST); ?>
<div class='form-container'>
<form class='well span4' id='signup-form' action='' method='POST'>
	<h1>Signup</h1>
	<br />
	<label for='first-name'>First Name</label>
	<input type='text' name='first-name' id='first-name' />
	<label for='last-name'>Last Name</label>
	<input type='text' name='last-name' id='last-name' />
	<label for='username'>Email</label>
	<input type='email' name='email' id='email' />
	<label for='password'>Password</label>
	<input type='password' name='password' id='password' />
	<label for='confirm-password'>Confirm Password</label>
	<input type='password' name='confirm-password' id='confirm-password' />
	<br />
	<button class='btn' type='submit'>Signup</button>
	<br />
	<br />
	<p>Already a member?<a href='login.php'>Login!</a></p>
</form>
</div>
<?php include_once("footer.php"); ?>