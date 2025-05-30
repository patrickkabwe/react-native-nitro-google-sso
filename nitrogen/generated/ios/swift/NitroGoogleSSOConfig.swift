///
/// NitroGoogleSSOConfig.swift
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

import NitroModules

/**
 * Represents an instance of `NitroGoogleSSOConfig`, backed by a C++ struct.
 */
public typealias NitroGoogleSSOConfig = margelo.nitro.nitrogooglesso.NitroGoogleSSOConfig

public extension NitroGoogleSSOConfig {
  private typealias bridge = margelo.nitro.nitrogooglesso.bridge.swift

  /**
   * Create a new instance of `NitroGoogleSSOConfig`.
   */
  init(nonce: String?, iosClientId: String, webClientId: String, hostedDomain: String?) {
    self.init({ () -> bridge.std__optional_std__string_ in
      if let __unwrappedValue = nonce {
        return bridge.create_std__optional_std__string_(std.string(__unwrappedValue))
      } else {
        return .init()
      }
    }(), std.string(iosClientId), std.string(webClientId), { () -> bridge.std__optional_std__string_ in
      if let __unwrappedValue = hostedDomain {
        return bridge.create_std__optional_std__string_(std.string(__unwrappedValue))
      } else {
        return .init()
      }
    }())
  }

  var nonce: String? {
    @inline(__always)
    get {
      return { () -> String? in
        if let __unwrapped = self.__nonce.value {
          return String(__unwrapped)
        } else {
          return nil
        }
      }()
    }
    @inline(__always)
    set {
      self.__nonce = { () -> bridge.std__optional_std__string_ in
        if let __unwrappedValue = newValue {
          return bridge.create_std__optional_std__string_(std.string(__unwrappedValue))
        } else {
          return .init()
        }
      }()
    }
  }
  
  var iosClientId: String {
    @inline(__always)
    get {
      return String(self.__iosClientId)
    }
    @inline(__always)
    set {
      self.__iosClientId = std.string(newValue)
    }
  }
  
  var webClientId: String {
    @inline(__always)
    get {
      return String(self.__webClientId)
    }
    @inline(__always)
    set {
      self.__webClientId = std.string(newValue)
    }
  }
  
  var hostedDomain: String? {
    @inline(__always)
    get {
      return { () -> String? in
        if let __unwrapped = self.__hostedDomain.value {
          return String(__unwrapped)
        } else {
          return nil
        }
      }()
    }
    @inline(__always)
    set {
      self.__hostedDomain = { () -> bridge.std__optional_std__string_ in
        if let __unwrappedValue = newValue {
          return bridge.create_std__optional_std__string_(std.string(__unwrappedValue))
        } else {
          return .init()
        }
      }()
    }
  }
}
