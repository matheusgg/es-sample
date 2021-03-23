package br.com.sample.controller

import br.com.sample.model.Quote
import br.com.sample.service.QuoteService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/quotes")
class QuoteController(private val service: QuoteService) {

    @PostMapping
    fun save(@RequestBody quote: Quote) = this.service.save(quote)

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody quote: Quote) = this.service.update(id, quote)

    @PatchMapping(path = ["/updateAuthorUsingQueryBuilder/{id}"], consumes = [APPLICATION_FORM_URLENCODED_VALUE])
    fun updateAuthorUsingQueryBuilder(@PathVariable id: String, @RequestParam author: String) =
        this.service.updateAuthorUsingQueryBuilder(id, author)

    @PatchMapping(path = ["/updateYear/{id}"], consumes = [APPLICATION_FORM_URLENCODED_VALUE])
    fun updateYear(@PathVariable id: String, @RequestParam year: Int) = this.service.updateYear(id, year)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) = this.service.delete(id)

    @GetMapping
    fun retrieveAll(@PageableDefault pageable: Pageable) = this.service.retrieveAll(pageable)

    @GetMapping("/retrieveByAuthor")
    fun retrieveByAuthor(@RequestParam author: String, @PageableDefault pageable: Pageable) =
        this.service.retrieveByAuthor(author, pageable)

    @GetMapping("/retrieveByAuthorUsingCustomQuery")
    fun retrieveByAuthorUsingCustomQuery(@RequestParam author: String) =
        this.service.retrieveByAuthorUsingCustomQuery(author)

    @GetMapping("/retrieveByContentAndYearUsingQueryBuilder")
    fun retrieveByContentAndYearUsingQueryBuilder(@RequestParam content: String, @RequestParam year: Int) =
        this.service.retrieveByContentAndYearUsingQueryBuilder(content, year)
}