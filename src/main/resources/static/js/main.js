
ingredients = [];

$(document).ready(function () {

    $('#form').submit(function (event) {

        event.preventDefault();
        event.returnValue = false;

        let $form = $(this);

        $.ajax({
            type: "POST",
            url: "/saveIngredients",
            context: $form,
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify(ingredients),
            complete: function () {
                this.off('submit');
                this.submit();
            }
        });
    });
});


function addIngredient() {

    let uomValue = document.getElementById("list");
    let ingredientAmount = document.getElementById("ingredient-amount").value;
    let ingredientDescription = document.getElementById("ingredient-description").value;

    //make an Ingredient Object
    let uom = {"description" : uomValue.value};
    let ingredient = {"ingredientDescription" : ingredientDescription, "amount" : ingredientAmount , "uom" : uom };

    ingredients.push(ingredient);

    let node = document.createElement("LI"); //make li node
    node.classList.add("list-group-item");  // add bootstrap class
    let textNode = document.createTextNode(ingredient.amount + " " + uom.description + " " + ingredient.ingredientDescription);  //how user sees list item

    node.appendChild(textNode); // add text to node
    document.getElementById("ingredient-list").appendChild(node);

}


//clears ingredient input values when adding
function clearInput() {
    document.getElementById("ingredient-amount").value = "";
    document.getElementById("ingredient-description").value = "";
}
