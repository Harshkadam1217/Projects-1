<?php
    $server = 'localhost';
    $username = 'root';
    $password = '';
    $database = 'to_do_list';

    $connection = mysqli_connect($server,$username,$password,$database);

    if($connection->connect_error){
        die('Connection to my sql failed '.$connection->connect_error);
    }
    $email=$_POST['email'];
    $password1=$_POST['password'];
    $password2=$_POST['currentpassword'];
    if($password1 == $password2){
        $query ="INSERT INTO signup values(NULL,'$email','$password1')";
        $result=mysqli_query($connection,$query); 
        echo '<center>
        <div class="alert alert-success" role="alert">
            Go back and login again
            <h1><a href="index.php">To Do List</a> </h1>
        </div>
    </center>

';
    }else{
        echo '
        <center>
        Please Give Both the passwords same
        <a href="index.php">Link</a>
        </center>
        ';
        
    }
    
?>
