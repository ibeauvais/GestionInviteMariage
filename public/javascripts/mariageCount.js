

function enterAddAdulte(){
	$('#modalAdulte').modal('show');

}

function closeAddAdulte(){
	$('#modalAdulte').modal('hide');
}



function addAdulte(idInvite){
	var nomAdulte=$('#adulteName').val();
    if(nomAdulte!=""){
        $.ajax({
            type:'POST',
            url:'rest/addAdulte',
            contentType:'application/json; charset=UTF-8',
            data:nomAdulte,
            dataType:'json',
            success: function(data){
                refreshAdulteWithData(data);
            }
        })
    }
}

function refreshAdulte(){

}

function refreshAdulteWithData(data){

}