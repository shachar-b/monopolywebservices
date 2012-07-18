<?php

include_once 'DataBase.php';

class LoginAuthanticator {

    private $m_data;
    private $m_DB;
    private $m_connection;

    public function __construct($i_data){
        $this->m_data = $i_data;
        $this->m_DB = Database::getInstance();
        $this->m_connection = $this->m_DB->getConnection();
    }

    public function authenticate(){
        if(isset($this->m_data['login'])){
            $user = $this->handleLogin();
            $args = array();
            if($user === false){
                $args['user_exists'] = false;
                $this->respond($args);
            }
            else {
                $args['user_exists'] = true;
                $args['user_name'] = "name";
                
                $this->respond($args);
            }
        }
        else {
            //$user = $this->handleSignup();
        }
    }

    private function handleLogin(){
        if(isset($this->m_data['email']) && isset($this->m_data['password'])){
            $email = $this->m_data['email'];
            $password = $this->m_data['password'];
            $res = $this->m_DB->query("SELECT * FROM users WHERE email='$email' AND password='$password'");
            $num_rows = mysql_num_rows($res);
            if(mysql_num_rows($res) != 1){
                return false;
            }
            else {
                return $user;
            }
        }
    }

    private function respond($args){
        return json_encode($args);
    }

}

?>
