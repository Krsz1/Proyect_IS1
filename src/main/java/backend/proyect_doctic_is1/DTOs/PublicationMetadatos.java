package backend.proyect_doctic_is1.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PublicationMetadatos {
    
    private String description;
    private List<Author> authors;
    private List<Category> categories;

    @Data
    public static class Author{
        private String username;
        private String userRoleAuthors;
    }

    @Data
    public static class Category{
        private String name;
    }
}
