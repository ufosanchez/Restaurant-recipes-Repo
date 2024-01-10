package com.project.v1.testing.recipesproject1.models

import retrofit2.http.Url

class Restaurant(var data : List<data>) {
    override fun toString(): String {
        return "Restaurant(data=$data)"
    }
}

class data(var location_id : String, var name: String = "", var location_string : String = "", var address : String = "", var photo : Images? =null,
           var price_level :String = "", var latitude : String = "", var longitude : String = "", var phone : String = "", var website : String = ""){
    override fun toString(): String {
        return "data(location_id='$location_id', name='$name', location_string='$location_string', address='$address', photo=$photo, price_level='$price_level', " +
                "latitude='$latitude', longitude='$longitude', phone='$phone', website='$website')"
    }
}

class Images(var images : Original){
    override fun toString(): String {
        return "Images(images=$images)"
    }

}

class Original(var original : Link){
    override fun toString(): String {
        return "Original(original=$original)"
    }
}

class Link(var url :String){
    override fun toString(): String {
        return "Link(url='$url')"
    }

}
