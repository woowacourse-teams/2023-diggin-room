package com.digginroom.digginroom.feature.scrap.viewmodel

sealed interface ScrapUiEvent {

    object Idle : ScrapUiEvent

    data class FailedSelection(val maximum: Int) : ScrapUiEvent

    object DisAllowedExtraction : ScrapUiEvent

    data class LoadingExtraction(val expectedTime: Int) : ScrapUiEvent
}
