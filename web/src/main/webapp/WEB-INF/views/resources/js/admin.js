window.onload = function () {
    getMethod('admin/genres', '#genre-list', "content");

    doc.querySelector('#users').onclick = function () {
        getMethod('admin/users', '#user-table', "content");
    }
    doc.querySelector('#bonuses').onclick = function () {
        getMethod('admin/bonuses', '#bonus-table', "content");
    }
    doc.querySelector('#genres').onclick = function () {
        getMethod('admin/genres', '#genre-list', "content");
    }
}

function inputId(id, element) {
    var album = doc.getElementById(element);
    album.value = id;
}

function addGenre(title, description, image) {
    putMethod('admin/genre/' +
              'title/' + title +
              '/description/' + description +
              '/image/' + image);
}

function addArtist(idGenre, title, description, image) {

    putMethod('admin/artist' +
              '/genre/' + idGenre +
              '/title/' + title +
              '/description/' + description +
              '/image/' + image)

}

function addAlbum() {

    var idArtist = getNode('artistId').value,
        title = getNode('titleAlbum').value,
        year = getNode('yearAlbum').value,
        description = getNode('descriptionAlbum').value,
        image = getNode('imageAlbum').value;

    putMethod('admin/album/' +
              'artist/' + idArtist +
              '/title/' + title +
              '/year/' + year +
              '/description/' + description +
              '/image/' + image);

}

function addSong() {
    var idAlbum = getNode('albumId').value,
        title = getNode('titleSong').value,
        duration = getNode('durationSong').value,
        price = getNode('priceSong').value;

    putMethod('admin/song/' +
              'album/' + idAlbum +
              '/title/' + title +
              '/duration/' + duration +
              '/price/' + price);
}

function addBonus() {
    var title = getNode('titleBonus').value,
        description = getNode('descriptionBonus').value,
        discount = getNode('discountBonus').value,
        code = getNode('codeBonus').value;

    putMethod('admin/bonus/' +
              'title/' + title +
              '/description/' + description +
              '/discount/' + discount +
              '/code/' + code);
}

function addUser() {
    var email = getNode('inputEmail').value,
        name = getNode('inputName').value,
        surname = getNode('inputSurname').value,
        password = getNode('inputPassword').value,
        photo = getNode('inputPhoto').value,
        login = getNode('inputLogin').value,
        role = getNode('inputRoot').value;

    if (role === 'ADMIN') {
        var root = 2;
    } else {
       root = 1;
    }

    putMethod('admin/user'
              + '/email/'  + email
              + '/login/' + login
              + '/name/'  + name
              + '/surname/' + surname
              + '/password/'  + password
              + '/photo/' + photo
              + '/role/'  + root);

}