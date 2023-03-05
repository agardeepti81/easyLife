function toggle(str1,str2){
    document.getElementById(str1).style.display = 'none';
    document.getElementById(str2).style.display = 'inline';
  }
  
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