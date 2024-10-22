package backend.proyect_doctic_is1.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PublicationMetadatos {
    
    private String description;
    private List<Author> authors;
    private List<Category> categories;
    private List<DocsFilesInfo> docsFilesInfo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publicationDate;
    
    @Data
    public static class Author{
        private String username;
        private String userRoleAuthors;
    }

    @Data
    public static class Category{
        private String name;
    }

    @Data
    public static class DocsFilesInfo{
        private int totalDownloads;
        private double avgRaiting;
        private int totalComments;
        private int totalViews;
    }
}
