package backend.proyect_doctic_is1.Model;

import backend.proyect_doctic_is1.Model.ENUM.visibility;
import backend.proyect_doctic_is1.Model.ENUM.userRoleAuthors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Publications")

public class PublicationsModel {

    @Id
    private String id;
    private String title;
    private String description;
    private LocalDate publicationDate;
    private String urlFiles;
    private String visibility; 
    
    private List<Authors> authors;
    private List<Category> categories;
    private List<Rating> ratings;
    private List<DocsFilesInfo> docsFilesInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor

    public static class Authors {
        private String idUser;
        private String username;
        private userRoleAuthors userRoleAuthors;  
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor

    public static class Category {
        private String idCategoria;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor

    public static class Rating {
        private String idUser;
        private LocalDate date;
        private int score;
        private String comments;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    
    public static class DocsFilesInfo {
        private int totalDownloads;
        private double avgRating;
        private int totalComments;
        private int totalViews;
    }
}