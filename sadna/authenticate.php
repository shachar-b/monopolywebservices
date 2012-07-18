<?php
include_once("DataBase.php");
include_once("utils.php");

class AuthenticateLogin {

    private $mDB;
    private $mConnection;

    public function __construct() {
        $this->mDB = Database::getInstance();
        $this->mConnection = $this->mDB->getConnection();
    }

    public function authenticate($data) {
        if (isset($data['email']) && isset($data['password'])) {
            $sql = "SELECT first_name, last_name, email, id FROM users WHERE ";
            $sql .= "email='" . $data['email'] . "' AND password='" . $data['password'] . "' LIMIT 1";
            $res = $this->mDB->query($sql);
            if ($res) {
                $user = mysql_fetch_array($res);
                $_SESSION['email'] = $user['email'];
                $_SESSION['id'] = $user['id'];
                $_SESSION['last_name'] = $user['last_name'];
                $_SESSION['first_name'] = $user['first_name'];
                if (isset($_GET['ret_url'])) {
                    header("Location: " . $_GET['ret_url']);
                } else {
                    header("Location: index.php");
                }
            } else {
                return false;
            }
        }
    }

    public function isLoggedin() {
        if (!isset($_SESSION['email'])) {
            header("Location: login.php?ret_url=" . Utils::getCurrentPage());
        }
    }

    public function signup($data) {
        if (isset($data['email'])) {
            $sql = "INSERT INTO users (first_name, last_name, email, password) VALUES(";
            $sql .= " '" . $data['first-name'] . "', '" . $data['last-name'] . "' ,'" . $data['email'] . "', '" . $data['password'] . "')";
            $this->mDB->query($sql);
            $sql = "";
            $sql = "INSERT INTO clients (user_id) VALUES(LAST_INSERT_ID())";
            $this->mDB->query($sql);
        } else {
            return false;
        }
    }

    public function redirects($data) {
        session_start();
        if (Utils::isInPage("login.php")) {
            $this->authenticate($data);
        } else if (Utils::isInPage("signup.php")) {
            $this->signup($data);
        } else {
            $this->isLoggedIn();
            if (isset($_POST['logout'])) {
                $this->logout();
                header("Location: login.php");
            }
        }
    }

    public function logout() {
        session_destroy();
    }

    public function setUserData() {
        if (isset($_SESSION['email'])) :
            ?>
            <script>
                window.GLOBALS = {};
                window.GLOBALS.USER = {};
                window.GLOBALS.USER.id = "<?php echo $_SESSION['id'] ?>";
                window.GLOBALS.USER.email = "<?php echo $_SESSION['email'] ?>";
                window.GLOBALS.USER.first_name = "<?php echo $_SESSION['first_name'] ?>";
                window.GLOBALS.USER.last_name = "<?php echo $_SESSION['last_name'] ?>";
            </script>
        <?php
        endif;
    }

}
?>