// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/protobuf/compiler/cpp/cpp_test_large_enum_value.proto

#ifndef PROTOBUF_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto__INCLUDED
#define PROTOBUF_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 3003000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 3003000 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/arena.h>
#include <google/protobuf/arenastring.h>
#include <google/protobuf/generated_message_table_driven.h>
#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/metadata.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>  // IWYU pragma: export
#include <google/protobuf/extension_set.h>  // IWYU pragma: export
#include <google/protobuf/generated_enum_reflection.h>
#include <google/protobuf/unknown_field_set.h>
// @@protoc_insertion_point(includes)
namespace protobuf_unittest {
class TestLargeEnumValue;
class TestLargeEnumValueDefaultTypeInternal;
extern TestLargeEnumValueDefaultTypeInternal _TestLargeEnumValue_default_instance_;
}  // namespace protobuf_unittest

namespace protobuf_unittest {

namespace protobuf_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto {
// Internal implementation detail -- do not call these.
struct TableStruct {
  static const ::google::protobuf::internal::ParseTableField entries[];
  static const ::google::protobuf::internal::AuxillaryParseTableField aux[];
  static const ::google::protobuf::internal::ParseTable schema[];
  static const ::google::protobuf::uint32 offsets[];
  static void InitDefaultsImpl();
  static void Shutdown();
};
void AddDescriptors();
void InitDefaults();
}  // namespace protobuf_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto

enum TestLargeEnumValue_EnumWithLargeValue {
  TestLargeEnumValue_EnumWithLargeValue_VALUE_1 = 1,
  TestLargeEnumValue_EnumWithLargeValue_VALUE_MAX = 2147483647
};
bool TestLargeEnumValue_EnumWithLargeValue_IsValid(int value);
const TestLargeEnumValue_EnumWithLargeValue TestLargeEnumValue_EnumWithLargeValue_EnumWithLargeValue_MIN = TestLargeEnumValue_EnumWithLargeValue_VALUE_1;
const TestLargeEnumValue_EnumWithLargeValue TestLargeEnumValue_EnumWithLargeValue_EnumWithLargeValue_MAX = TestLargeEnumValue_EnumWithLargeValue_VALUE_MAX;
const ::google::protobuf::EnumDescriptor* TestLargeEnumValue_EnumWithLargeValue_descriptor();
inline const ::std::string& TestLargeEnumValue_EnumWithLargeValue_Name(TestLargeEnumValue_EnumWithLargeValue value) {
  return ::google::protobuf::internal::NameOfEnum(
    TestLargeEnumValue_EnumWithLargeValue_descriptor(), value);
}
inline bool TestLargeEnumValue_EnumWithLargeValue_Parse(
    const ::std::string& name, TestLargeEnumValue_EnumWithLargeValue* value) {
  return ::google::protobuf::internal::ParseNamedEnum<TestLargeEnumValue_EnumWithLargeValue>(
    TestLargeEnumValue_EnumWithLargeValue_descriptor(), name, value);
}
// ===================================================================

class TestLargeEnumValue : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:protobuf_unittest.TestLargeEnumValue) */ {
 public:
  TestLargeEnumValue();
  virtual ~TestLargeEnumValue();

  TestLargeEnumValue(const TestLargeEnumValue& from);

  inline TestLargeEnumValue& operator=(const TestLargeEnumValue& from) {
    CopyFrom(from);
    return *this;
  }

  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _internal_metadata_.unknown_fields();
  }

  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return _internal_metadata_.mutable_unknown_fields();
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const TestLargeEnumValue& default_instance();

  static inline const TestLargeEnumValue* internal_default_instance() {
    return reinterpret_cast<const TestLargeEnumValue*>(
               &_TestLargeEnumValue_default_instance_);
  }
  static PROTOBUF_CONSTEXPR int const kIndexInFileMessages =
    0;

  void Swap(TestLargeEnumValue* other);

  // implements Message ----------------------------------------------

  inline TestLargeEnumValue* New() const PROTOBUF_FINAL { return New(NULL); }

  TestLargeEnumValue* New(::google::protobuf::Arena* arena) const PROTOBUF_FINAL;
  void CopyFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void MergeFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void CopyFrom(const TestLargeEnumValue& from);
  void MergeFrom(const TestLargeEnumValue& from);
  void Clear() PROTOBUF_FINAL;
  bool IsInitialized() const PROTOBUF_FINAL;

  size_t ByteSizeLong() const PROTOBUF_FINAL;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input) PROTOBUF_FINAL;
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const PROTOBUF_FINAL;
  ::google::protobuf::uint8* InternalSerializeWithCachedSizesToArray(
      bool deterministic, ::google::protobuf::uint8* target) const PROTOBUF_FINAL;
  int GetCachedSize() const PROTOBUF_FINAL { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const PROTOBUF_FINAL;
  void InternalSwap(TestLargeEnumValue* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return NULL;
  }
  inline void* MaybeArenaPtr() const {
    return NULL;
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const PROTOBUF_FINAL;

  // nested types ----------------------------------------------------

  typedef TestLargeEnumValue_EnumWithLargeValue EnumWithLargeValue;
  static const EnumWithLargeValue VALUE_1 =
    TestLargeEnumValue_EnumWithLargeValue_VALUE_1;
  static const EnumWithLargeValue VALUE_MAX =
    TestLargeEnumValue_EnumWithLargeValue_VALUE_MAX;
  static inline bool EnumWithLargeValue_IsValid(int value) {
    return TestLargeEnumValue_EnumWithLargeValue_IsValid(value);
  }
  static const EnumWithLargeValue EnumWithLargeValue_MIN =
    TestLargeEnumValue_EnumWithLargeValue_EnumWithLargeValue_MIN;
  static const EnumWithLargeValue EnumWithLargeValue_MAX =
    TestLargeEnumValue_EnumWithLargeValue_EnumWithLargeValue_MAX;
  static inline const ::google::protobuf::EnumDescriptor*
  EnumWithLargeValue_descriptor() {
    return TestLargeEnumValue_EnumWithLargeValue_descriptor();
  }
  static inline const ::std::string& EnumWithLargeValue_Name(EnumWithLargeValue value) {
    return TestLargeEnumValue_EnumWithLargeValue_Name(value);
  }
  static inline bool EnumWithLargeValue_Parse(const ::std::string& name,
      EnumWithLargeValue* value) {
    return TestLargeEnumValue_EnumWithLargeValue_Parse(name, value);
  }

  // accessors -------------------------------------------------------

  // @@protoc_insertion_point(class_scope:protobuf_unittest.TestLargeEnumValue)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::internal::HasBits<1> _has_bits_;
  mutable int _cached_size_;
  friend struct protobuf_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto::TableStruct;
};
// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// TestLargeEnumValue

#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)


}  // namespace protobuf_unittest

#ifndef SWIG
namespace google {
namespace protobuf {

template <> struct is_proto_enum< ::protobuf_unittest::TestLargeEnumValue_EnumWithLargeValue> : ::google::protobuf::internal::true_type {};
template <>
inline const EnumDescriptor* GetEnumDescriptor< ::protobuf_unittest::TestLargeEnumValue_EnumWithLargeValue>() {
  return ::protobuf_unittest::TestLargeEnumValue_EnumWithLargeValue_descriptor();
}

}  // namespace protobuf
}  // namespace google
#endif  // SWIG

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto__INCLUDED
