package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GalleryDataModel {

    @SerializedName("Success")
    @Expose
    private String success;

    @SerializedName("FinalArray")
    @Expose
    private ArrayList<GalleryFinalArray> finalArray;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<GalleryFinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(ArrayList<GalleryFinalArray> finalArray) {
        this.finalArray = finalArray;
    }

    public class GalleryFinalArray{

        @SerializedName("Title")
        @Expose
        private String title;

        @SerializedName("Comment")
        @Expose
        private String comment;

        @SerializedName("Date")
        @Expose
        private String date;

        @SerializedName("GalleryID")
        @Expose
        private String galleryid;

        @SerializedName("Photos")
        @Expose
        private ArrayList<photosFinalArray> photos;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getGalleryid() {
            return galleryid;
        }

        public void setGalleryid(String galleryid) {
            this.galleryid = galleryid;
        }

        public ArrayList<photosFinalArray> getPhotos() {
            return photos;
        }

        public void setPhotos(ArrayList<photosFinalArray> photos) {
            this.photos = photos;
        }
    }

    public static class photosFinalArray{

        @SerializedName("DetaiID")
        @Expose
        private String detailid;

        @SerializedName("ImagePath")
        @Expose
        private String imagepath;

        public String getDetailid() {
            return detailid;
        }

        public void setDetailid(String detailid) {
            this.detailid = detailid;
        }

        public String getImagepath() {
            return imagepath;
        }

        public void setImagepath(String imagepath) {
            this.imagepath = imagepath;
        }
    }
}
