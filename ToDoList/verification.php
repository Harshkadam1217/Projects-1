<html>
    <head>
        <title>Email and password verification</title>
    </head>
    <body>
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
    $query ="SELECT * from signup";
    $result=mysqli_query($connection,$query); 
    $flag=0;
    while( $row = $result->fetch_assoc()) {
        $email1=$row['email'];
        $password2=$row['pass'];
        if($email==$email1 && $password1==$password2){
            
            $flag=1;
            break;
            ;
        }
        else{
            $flag=0;
        }
    }
    if($flag==1){
        echo '
            <center>
                        <div class="alert alert-success" role="alert">
                            Done
                            <h1><a href="todolist.php">To Do List</a> </h1>
                        </div>
                    </center>';
    }
    else{
        echo '
        <center>
                        <div class="alert alert-danger" role="alert">
                            This email already exists or you havent created it yet so go back thank you
                            <h1><a href="index.php">Go Back</a> </h1>
                        </div>
                    </center>
        
        ';
    }
?>

    </body>
</html>