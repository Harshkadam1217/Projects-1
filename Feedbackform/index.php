<?php
    $server = 'localhost';
    $username = 'root';
    $password = '';
    $database = 'feedbackform';
    $connection = mysqli_connect($server,$username,$password,$database);
    if($connection->connect_error){
        die('Connection to my sql failed '.$connection->connect_error);
    }
    $pname=$pversion=$dop=$performance=$eou=$reliability=$vfm=$overall=$ac='';
    $pname = $_POST['pname'] ?? '';
    $pversion = $_POST['pversion'] ?? '';
    $dop = $_POST['dop'] ?? '';
    $overall = $_POST['overall'] ?? '';
    $performance = $_POST['performance'] ?? '';
    $eou = $_POST['eou'] ?? '';
    $reliability = $_POST['reliability'] ?? '';
    $vfm = $_POST['vfm'] ?? '';
    $ac = $_POST['ac'] ?? '';
    
    
    
    // $dop=$_POST['dop'];
    // $overall=$_POST['overall'];
    // $performance=$_POST['performance'];
    // $eou=$_POST['eou'];
    // $reliability=$_POST['reliability'];
    // $vfm=$_POST['vfm'];
    // $ac=$_POST['ac'];
    $query ="INSERT INTO feedback values(NULL,'$pname','$pversion','$dop','$performance','$eou','$reliability','$vfm','$overall','$ac');";
    if(mysqli_query($connection,$query)){
        
      
    }else{
        echo mysqli_error($connection);

    }
?> 

<!DOCTYPE html>
<html>
    <head>
        <title>Product Feedback Form</title>
        <style>
            /* Center the form */
            body {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
                font-family: Arial, sans-serif;
            }

            /* Style the form container */
            .form-container {
                text-align: left;
                padding: 20px;
                border: 1px solid #130000;
                border-radius: 5px;
                background-color: #aaa3a338;
                width: 800px;
                box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
            }

            /* Create a two-column layout */
            .form-row {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 10px;
            }

            .form-row label,
            .form-row input,
            .form-row textarea {
                flex: 1;
            }

            /* Style form elements */
            label {
                font-weight: bold;
            }

            input[type="text"],
            input[type="email"],
            input[type="tel"],
            input[type="number"],
            textarea {
                width: 100%;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 3px;
            }

            input[type="radio"],
            input[type="checkbox"] {
                margin-right: 5px;
            }

            /* Style submit button */
            input[type="submit"] {
                background-color: #05812a;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 3px;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            .header1{
                
            }

            input[type="submit"]:hover {
                background-color: #023b25;
            }
        </style>
    </head>
    <body>
        <div class="form-container">
            <h1 class="header1">Product Feedback Form</h1> <br>
            <form action="<?= $_SERVER['PHP_SELF']?>" method="POST">
                <div class="form-row">
                    <label for="pname">Product Name:</label>
                    <input type="text"  name="pname" required>
                </div>
                <div class="form-row">
                    <label for="product-version">Product Version:</label>
                    <input type="text"  name="pversion">
                </div>
                <div class="form-row">
                    <label for="purchase-date">Date of Purchase:</label>
                    <input type="date"  name="dop">
                </div>
                <!-- Add more form rows as needed for other fields -->
                
                <!-- Feedback Questions -->
                
                <div class="form-row">
                    <label for="performance">Performance (1 - 5):</label>
                    <input type="number"  name="performance" min="1" max="5">
                </div>
                <div class="form-row">
                    <label for="ease-of-use">Ease of Use (1 - 5):</label>
                    <input type="number"  name="eou" min="1" max="5">
                </div>
                <div class="form-row">
                    <label for="reliability">Reliability (1 - 5):</label>
                    <input type="number"  name="reliability" min="1" max="5">
                </div>
                <div class="form-row">
                    <label for="value-for-money">Value for Money (1 - 5):</label>
                    <input type="number" name="vfm" min="1" max="5">
                </div>
                <div class="form-row">
                    <label for="satisfaction">Overall Satisfaction:</label>
                    <!-- Add radio buttons here -->
                    <input type="number" name="overall" min="1" max="5">
                </div>
        
                <!-- Additional Comments -->
                <div class="form-row">
                    <label for="comments">Additional Comments:</label>
                    <textarea name="ac" rows="4" cols="50"></textarea>
                </div>
        
                <!-- Submit Button -->
                <div class="form-row">
                    <input type="submit" value="Submit">
                </div>
            </form>
        </div>
    </body>
</html>


