var onEnter = function (arg) {

    API.getPersistenceObject().putProperty('text', arg);
    return true;
}