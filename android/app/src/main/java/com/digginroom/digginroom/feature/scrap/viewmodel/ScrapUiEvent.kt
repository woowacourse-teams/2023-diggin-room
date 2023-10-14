package com.digginroom.digginroom.feature.scrap.viewmodel

sealed interface ScrapUiEvent {

    object Idle : ScrapUiEvent

    object DisAllowedExtraction : ScrapUiEvent

    data class LoadingExtraction(val expectedTime: Int) : ScrapUiEvent
}
