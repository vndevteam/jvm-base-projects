package com.vndevteam.kotlinwebspringboot3.util

import com.vndevteam.kotlinwebspringboot3.common.constants.SecurityConstants

fun String?.doesNotContainBearerToken() = this == null || !this.startsWith(SecurityConstants.BEARER)

fun String.extractTokenValue() = this.substringAfter(SecurityConstants.BEARER)
