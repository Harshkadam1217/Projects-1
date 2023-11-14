<?php 
    $server = 'localhost';
    $username = 'root';
    $password = '';
    $database = 'to_do_list';

    $connection = mysqli_connect($server,$username,$password,$database);

    if($connection->connect_error){
        die('Connection to my sql failed '.$connection->connect_error);
    }

    //creating todo item
    if(isset($_POST['add'])){
        $item=$_POST['item'];
        if(!empty($item)){
            $query ="INSERT INTO todolist(name) values ('$item')";
           
            
            if(mysqli_query($connection,$query)){
                echo '
               <center>
               <div class="alert alert-success" role="alert">
               Item Added Successfully!
           </div>
               </center>
                ';
            }else{
                echo mysqli_error($connection);

            }
        }
    }


    //checking if action parameter is present
    if(isset($_GET['action'])){
        $itemid=$_GET['item'];
        if($_GET['action']=='done'){
            $query ="UPDATE todolist SET status=1 where id ='$itemid'";
           
            
            if(mysqli_query($connection,$query)){
                echo '
                <center>
                    <div class="alert alert-info" role="alert">
                        Item Marked as Done!
                    </div>
                </center>
                ';
            }else{
                echo mysqli_error($connection);

            }
        }
    }

    //Deleting the Operations
    if(isset($_GET['action'])){
        $itemid=$_GET['item'];
        if($_GET['action']=='delete'){
            $query ="DELETE from todolist where id='$itemid'";
            if(mysqli_query($connection,$query)){
                echo '
                <center>
                    <div class="alert alert-danger" role="alert">
                        Item Deleted Suc
                        cessfully!
                    </div>
                </center> 
                ';
            }else{
                echo mysqli_error($connection);

            }
        }
    }

?>

<!DOCTYPE html>
<html>
    <head>
        <title>To Do List Application</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <style>
            .done{
                text-decoration: line-through;
            }
        </style>
    </head>
    <body>
        <main>
            
            <div class="container pt-5">
                <div class="row">
                    <div class="col-sm-12 col-md-3"></div>
                    <div class="col-sm-12 col-md-6">
                        <div class="card">
                            <div class="card-header">
                                <p>TODO LIST</p>
                                <div class="card-body">
                                    <form method="POST" action ="<?= $_SERVER['PHP_SELF']?>">

                                        <div>
                                            <input type="text" class="form-control" name="item" placeholder="Add a Item"><br>

                                        </div>
                                        <input type="submit" class="btn btn-dark" name="add" value="Add Item">
                                    </form>
                                    <div class="mt-5 mb-5">

                                        <?php
                                            $query ="SELECT * FROM todolist";
                                            $result=mysqli_query($connection,$query); 

                                            if($result->num_rows > 0){
                                                $i=1;
                                                while( $row = $result->fetch_assoc()) {
                                                    $done = $row['status']==1 ? "done":"";
                                                    echo '
                                                    <div class="row mt-4" >
                                                        <div class="col-sm-12 col-md-1"><h5>'.$i.'</h5></div>
                                                        <div class="col-sm-12 col-md-5"><h5 class="'.$done.'">'.$row['name'].'</h5></div>
                                                        <div class="col-sm-12 col-md-6">
                                                            <a href="?action=done&item='.$row['id'].'" class="btn btn-outline-dark">Mark as Done</a>
                                                            <a href="?action=delete&item='.$row['id'].'" class="btn btn-outline-danger">Delete</a>
                                                        </div>
                                                    </div> 
                                                    ';
                                                    $i++;
                                                }
                                            }
                                            else{
                                                $query ="TRUNCATE TABLE todolist";
                                                mysqli_query($connection, $query);
                                                echo '
                                                <center>
                                                <img src="emptyfolder.png" width="100px" alt="No tasks yet"><br><span><b>Your List is Empty</b></span>
                                                </center>
                                                ';
                                            }
                                        ?>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script>
            $(document).ready(function(){
                $(".alert").fadeTo(5000,500).slideUp(500,function(){
                    $(".alert").slideUp(5000,500);
                })
            })
        </script>
    </body>
</html>