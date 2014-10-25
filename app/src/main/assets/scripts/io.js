var onEnter = function (arg) {

    var persistence = API.getPersistenceObject();

    persistence.button = "Click";
    persistence.search = "Example";
    persistence.text = persistence.test;

    return true;
}