
<?php include_once "header.php"; ?>
<div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          
          <a class="brand" href="./index.php">Chat</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <!-- add links here -->
              <li class="active">
                <a href="PAGE">PAGE</a>
              </li>
              <!-- more links... -->
            </ul>
          </div>
        </div>
      </div>
    </div>
    
	<div class='container'>
		<div id='chatbox'></div>
	</div>
	<script> $(function(){$("#chatbox").chatbox({user: GLOBALS.USER});});</script>
<?php include_once "footer.php"; ?>