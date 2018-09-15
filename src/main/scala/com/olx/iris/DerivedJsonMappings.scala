package com.olx.iris

import spray.json.{ DefaultJsonProtocol, DerivedFormats }

trait DerivedJsonMappings extends DefaultJsonProtocol with DerivedFormats {}
