package backend.proyect_doctic_is1.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.proyect_doctic_is1.Model.PublicationsModel;
import backend.proyect_doctic_is1.Repository.IPublicationsRepository;

@Service
public class PublicationsServiceImp implements IPublicationsService {

    @Autowired
    private IPublicationsRepository publicationsRepository;

    @Autowired
    private MongoTemplate mongoTemplate;  // Inyectar MongoTemplate correctamente

    @Override
    public List<PublicationsModel> listAll() {
        return publicationsRepository.findAll();
    }

    @Override
    public List<PublicationsModel> searchPublicationsByTitleOrDescription(String keyword) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
            Criteria.where("title").regex(keyword, "i"),
            Criteria.where("description").regex(keyword, "i")
        ));
        return mongoTemplate.find(query, PublicationsModel.class);
    }

    @Override
    public List<PublicationsModel> filterPublications(LocalDate startDate, LocalDate endDate, String categoryId, String keyword, String description) {
        Query query = new Query();

        if (startDate != null && endDate != null) {
            query.addCriteria(Criteria.where("publicationDate").gte(startDate).lte(endDate));
        }
        if (categoryId != null && !categoryId.isEmpty()) {
            query.addCriteria(Criteria.where("categories.idCategoria").is(categoryId));
        }
        if (keyword != null && !keyword.isEmpty()) {
            query.addCriteria(new Criteria().orOperator(
                Criteria.where("title").regex(keyword, "i"),
                Criteria.where("description").regex(keyword, "i")
            ));
        }
        if (description != null && !description.isEmpty()) {
            query.addCriteria(Criteria.where("description").regex(description, "i"));
        }

        return mongoTemplate.find(query, PublicationsModel.class);
    }
    
}
