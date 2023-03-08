// Function to switch from login form to signup form

function toggle(str1,str2){
    document.getElementById(str1).style.display = 'none';
    document.getElementById(str2).style.display = 'inline';
  }
  
  // Function to check password matching while signing up

  var password = document.getElementById("password");
  var confirm_password = document.getElementById("confirmP");
  
  function validatePassword(){
    if(password.value != confirm_password.value) {
      confirm_password.setCustomValidity("Passwords Don't Match");
    } else {
      confirm_password.setCustomValidity('');
    }
  }
  
  password.onchange = validatePassword;
  confirm_password.onkeyup = validatePassword;

// Function to go back on the previous page

function goback(){
    let num = history.length;
    switch(num){
        case 1 : case 2:history.go(0);
        break;
        case 4: case 3: history.go(-1);
        break;
        case 5: history.go(-2);
        break;
        default: history.go(-1*(history.length-3))
        break;
    }
}

// Function to show the add items form

function showInputForm(id){
    let button = document.getElementById(id);
    let categoryName = button.getAttribute("param");
    document.getElementById("CategoryTitle").innerText = categoryName;
    document.getElementById("itemInputForm").style.display = 'inline';
    button.setAttribute("class","bg-success text-white");
    let form= document.getElementById("form");
    path = form.getAttribute("action");
    path += "/"+id;
    form.setAttribute("action",path);
}

function showUserData(){
  document.getElementById("viewUsers").style.display = 'inline';
  document.getElementById("viewAuthGroup").style.display = 'none';
  document.getElementById("viewAllCategories").style.display = 'none';
  document.getElementById("viewAllItems").style.display = 'none';
}

function showAuthGroupUsers(){
  document.getElementById("viewAuthGroup").style.display = 'inline';
  document.getElementById("viewUsers").style.display = 'none';
  document.getElementById("viewAllCategories").style.display = 'none';
  document.getElementById("viewAllItems").style.display = 'none';
}

function showCategoryData(){
  document.getElementById("viewAllCategories").style.display = 'inline';
  document.getElementById("viewUsers").style.display = 'none';
  document.getElementById("viewAuthGroup").style.display = 'none';
  document.getElementById("viewAllItems").style.display = 'none';
}

function showItemData(){
  document.getElementById("viewAllItems").style.display = 'inline';
  document.getElementById("viewAllCategories").style.display = 'none';
  document.getElementById("viewUsers").style.display = 'none';
  document.getElementById("viewAuthGroup").style.display = 'none';
}

function showAll(){
  document.getElementById("viewUsers").style.display = 'inline';
  document.getElementById("viewAuthGroup").style.display = 'inline';
  document.getElementById("viewAllCategories").style.display = 'inline';
  document.getElementById("viewAllItems").style.display = 'inline';
}