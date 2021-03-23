package br.com.sample.repository

import br.com.sample.model.Quote
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.annotations.Query
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface QuoteRepository : ElasticsearchRepository<Quote, String> {

    fun findByAuthor(author: String, pageable: Pageable): Page<Quote>

    @Query("{\"bool\": {\"must\": [{\"match\": {\"author\": \"?0\"}}]}}")
    fun findByAuthorUsingCustomQuery(author: String): Quote?
}