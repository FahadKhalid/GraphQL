package com.app.graphqlexample.data

import com.apollographql.apollo.ApolloClient
import com.app.CountriesQuery
import com.app.CountryQuery
import com.app.graphqlexample.domain.CountryClient
import com.app.graphqlexample.domain.DetailedCountry
import com.app.graphqlexample.domain.SimpleCountry

class ApolloCountryClient(
    private val apolloClient: ApolloClient
): CountryClient {

    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map { it.toSimpleCountry() }
            ?: emptyList()
    }

    override suspend fun getCountry(code: String): DetailedCountry? {
        return apolloClient
            .query(CountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailedCountry()
    }
}