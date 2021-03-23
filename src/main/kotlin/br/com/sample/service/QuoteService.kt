package br.com.sample.service

import br.com.sample.model.Quote
import br.com.sample.repository.QuoteRepository
import org.elasticsearch.index.query.QueryBuilders.boolQuery
import org.elasticsearch.index.query.QueryBuilders.matchQuery
import org.elasticsearch.index.query.QueryBuilders.rangeQuery
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates.of
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class QuoteService(private val repository: QuoteRepository, private val template: ElasticsearchRestTemplate) {

    fun save(quote: Quote) = this.repository.save(quote)

    fun update(id: String, quote: Quote): Quote {
        quote.id = id
        return this.repository.save(quote)
    }

    fun updateAuthorUsingQueryBuilder(id: String, author: String): Quote? {
        val query = NativeSearchQueryBuilder()
            .withQuery(matchQuery("_id", id).minimumShouldMatch("90%"))
            .build()

        val result = this.template.search(query, Quote::class.java, of("quotes"))

        return result.firstOrNull()?.let {
            val content = it.content
            content.author = author
            this.repository.save(content)
        }
    }

    fun updateYear(id: String, year: Int) = this.repository.findByIdOrNull(id)?.let {
        it.year = year
        this.repository.save(it)
    }

    fun delete(id: String) = this.repository.deleteById(id)

    fun retrieveAll(pageable: Pageable) = this.repository.findAll(pageable)

    fun retrieveByAuthor(author: String, pageable: Pageable) = this.repository.findByAuthor(author, pageable)

    fun retrieveByAuthorUsingCustomQuery(author: String) = this.repository.findByAuthorUsingCustomQuery(author)

    fun retrieveByContentAndYearUsingQueryBuilder(content: String, year: Int): List<Quote> {
        val queryBuilder = boolQuery()
            .must(rangeQuery("year").lte(year))
            .must(matchQuery("content", content))

        val query = NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .build()

        val result = this.template.search(query, Quote::class.java, of("quotes"))

        return result.map { it.content }.toList()
    }
}