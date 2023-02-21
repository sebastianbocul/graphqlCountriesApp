package com.sebix.graphqlcountriesapp.data

import com.apollographql.apollo3.ApolloClient
import com.sebix.CountriesQuery
import com.sebix.CountryQuery
import com.sebix.graphqlcountriesapp.domain.CountryClient
import com.sebix.graphqlcountriesapp.domain.DetailedCountry
import com.sebix.graphqlcountriesapp.domain.SimpleCountry

class ApolloCountryClient(
    private val apolloClient: ApolloClient
) : CountryClient {
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