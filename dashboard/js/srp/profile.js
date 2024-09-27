function getProfile() {
    $.getJSON("data/profile1.json", function(data){
        if (data){
        console.log(data.project_environment); 
        console.log(data.concurrent_sessions); 
        }
    }).fail(function(){
        console.log("An error has occurred.");
    });
}